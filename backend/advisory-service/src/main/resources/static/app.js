const API_BASE = window.location.origin;

const output = document.getElementById("output");
const assessmentSummary = document.getElementById("assessmentSummary");
const reportCreateSummary = document.getElementById("reportCreateSummary");
const reportSummary = document.getElementById("reportSummary");

function pad(n) {
    return String(n).padStart(2, "0");
}

function localInputValue(date = new Date()) {
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
}

function hoursOffsetValue(hours) {
    return localInputValue(new Date(Date.now() + hours * 60 * 60 * 1000));
}

function toIso(localValue) {
    return localValue ? new Date(localValue).toISOString() : null;
}

function isoToLocalInput(isoValue) {
    if (!isoValue) return "";
    const d = new Date(isoValue);
    return localInputValue(d);
}

function prettyJson(data) {
    output.textContent = JSON.stringify(data, null, 2);
}

function badgeClass(status) {
    const value = String(status || "").toLowerCase();
    if (value.includes("completed")) return "status-completed";
    if (value.includes("partial")) return "status-partial";
    if (value.includes("failed")) return "status-failed";
    return "status-pending";
}

function statusPill(status) {
    return `<span class="status-pill ${badgeClass(status)}"><i class="bi bi-circle-fill"></i>${status || "-"}</span>`;
}

function metric(label, value) {
    return `
        <div class="metric-card">
            <div class="metric-label">${label}</div>
            <div class="metric-value">${value ?? "-"}</div>
        </div>
    `;
}

function escapeHtml(value) {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function notesHtml(items) {
    if (!items || items.length === 0) {
        return `<div class="small-muted">No completeness issues.</div>`;
    }

    return `
        <ul class="list-clean">
            ${items.map(item => `<li>${escapeHtml(typeof item === "string" ? item : item.message || JSON.stringify(item))}</li>`).join("")}
        </ul>
    `;
}

function sanitizeErrorPayload(payload) {
    if (!payload || typeof payload !== "object") {
        return { error: "Request failed" };
    }

    return {
        timestamp: payload.timestamp,
        status: payload.status,
        error: payload.error,
        message: payload.message,
        path: payload.path
    };
}

async function apiFetch(url, options = {}) {
    const response = await fetch(url, {
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        },
        ...options
    });

    let data = null;
    try {
        data = await response.json();
    } catch (e) {
        data = null;
    }

    if (!response.ok) {
        const error = new Error(`HTTP ${response.status}`);
        error.payload = sanitizeErrorPayload(data || {
            status: response.status,
            error: response.statusText || "Request failed"
        });
        throw error;
    }

    return data;
}

function copyToClipboard(text) {
    navigator.clipboard.writeText(text).catch(() => {});
}

function renderAssessmentSummary(data) {
    const energy = data.energyConsumptionData;
    const weather = data.weatherContextData;
    const carbon = data.carbonIntensityData;

    assessmentSummary.innerHTML = `
        <div class="summary-grid">
            <div>${statusPill(data.assessmentStatus)}</div>

            <div class="panel-card">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                    <div>
                        <div class="panel-title mb-2">Assessment Overview</div>
                        <div class="small-muted">Use this result directly to generate the report.</div>
                    </div>
                    <div class="inline-actions">
                        <button class="copy-btn" type="button" onclick="copyAssessmentId('${escapeHtml(data.assessmentId || "")}')">
                            <i class="bi bi-copy me-1"></i>Copy assessmentId
                        </button>
                        <button class="copy-btn" type="button" onclick="useAssessmentForReportPayload()">
                            <i class="bi bi-arrow-down-circle me-1"></i>Use in report form
                        </button>
                    </div>
                </div>
            </div>

            <div class="metrics-grid">
                ${metric("Assessment ID", escapeHtml(data.assessmentId || "-"))}
                ${metric("Started At", escapeHtml(data.startedAt || "-"))}
                ${metric("Completed At", escapeHtml(data.completedAt || "-"))}
                ${energy ? metric("Average Consumption", `${energy.averageConsumption ?? "-"} ${energy.unit ?? ""}`.trim()) : ""}
                ${energy ? metric("Peak Consumption", `${energy.peakConsumption ?? "-"} ${energy.unit ?? ""}`.trim()) : ""}
                ${weather ? metric("Average Temperature", `${weather.averageTemperature ?? "-"} °C`) : ""}
                ${weather ? metric("Weather Summary", escapeHtml(weather.conditionSummary || "-")) : ""}
                ${carbon ? metric("Average Carbon Intensity", `${carbon.averageCarbonIntensity ?? "-"} ${carbon.unit ?? ""}`.trim()) : ""}
            </div>

            <div class="panel-card">
                <div class="panel-title">Data Completeness Notes</div>
                ${notesHtml(data.dataCompletenessNotes)}
            </div>

            ${data.errorMessage ? `
                <div class="error-box">
                    <strong>Error:</strong> ${escapeHtml(data.errorMessage)}
                </div>
            ` : ""}
        </div>
    `;

    window.__latestAssessment = data;
}

function renderReportCreateSummary(data) {
    reportCreateSummary.innerHTML = `
        <div class="summary-grid">
            <div class="panel-card">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                    <div>
                        <div class="panel-title mb-2">Report Generated</div>
                        <div class="small-muted">Use the generated reportId to fetch the full report.</div>
                    </div>
                    <div class="inline-actions">
                        <button class="copy-btn" type="button" onclick="copyReportId('${escapeHtml(data.reportId || "")}')">
                            <i class="bi bi-copy me-1"></i>Copy reportId
                        </button>
                        <button class="copy-btn" type="button" onclick="useReportId('${escapeHtml(data.reportId || "")}')">
                            <i class="bi bi-arrow-down-circle me-1"></i>Use in fetch
                        </button>
                    </div>
                </div>
            </div>

            <div class="metrics-grid">
                ${metric("Report ID", escapeHtml(data.reportId || "-"))}
                ${metric("Generated At", escapeHtml(data.generatedAt || "-"))}
                ${metric("Version", escapeHtml(data.reportVersion || "-"))}
                ${data.summaryMetrics ? metric("Total kWh", data.summaryMetrics.totalKWh ?? "-") : ""}
                ${data.summaryMetrics ? metric("Peak kWh", data.summaryMetrics.peakKWh ?? "-") : ""}
                ${data.summaryMetrics ? metric("Estimated Kg CO2", data.summaryMetrics.estimatedKgCO2 ?? "-") : ""}
            </div>
        </div>
    `;
}

function renderReportSummary(data) {
    const recs = Array.isArray(data.recommendations) ? data.recommendations : [];
    const contextNotes = Array.isArray(data.contextNotes) ? data.contextNotes : [];

    reportSummary.innerHTML = `
        <div class="summary-grid">
            <div class="panel-card">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                    <div>
                        <div class="panel-title mb-2">Report Overview</div>
                        <div class="small-muted">Fetched successfully using reportId.</div>
                    </div>
                    <div class="inline-actions">
                        <button class="copy-btn" type="button" onclick="copyReportId('${escapeHtml(data.reportId || "")}')">
                            <i class="bi bi-copy me-1"></i>Copy reportId
                        </button>
                    </div>
                </div>
            </div>

            <div class="metrics-grid">
                ${metric("Report ID", escapeHtml(data.reportId || "-"))}
                ${metric("Generated At", escapeHtml(data.generatedAt || "-"))}
                ${metric("Version", escapeHtml(data.reportVersion || "-"))}
                ${data.summaryMetrics ? metric("Total kWh", data.summaryMetrics.totalKWh ?? "-") : ""}
                ${data.summaryMetrics ? metric("Peak kWh", data.summaryMetrics.peakKWh ?? "-") : ""}
                ${data.summaryMetrics ? metric("Estimated Kg CO2", data.summaryMetrics.estimatedKgCO2 ?? "-") : ""}
            </div>

            <div class="panel-card">
                <div class="panel-title">Recommendations</div>
                ${
                    recs.length
                        ? `<div class="row g-3">
                            ${recs.map(rec => `
                                <div class="col-md-6">
                                    <div class="recommendation-card">
                                        <div class="recommendation-top">
                                            <div class="recommendation-id">${escapeHtml(rec.recommendationId || "Recommendation")}</div>
                                            <div class="recommendation-priority">${escapeHtml(rec.priority || "-")}</div>
                                        </div>
                                        <div class="small-muted mb-2">${escapeHtml(rec.category || "-")}</div>
                                        <div class="mb-2">${escapeHtml(rec.actionText || "-")}</div>
                                        <div class="small-muted"><strong>Impact:</strong> ${escapeHtml(rec.expectedImpact || "-")}</div>
                                    </div>
                                </div>
                            `).join("")}
                        </div>`
                        : `<div class="alert-lite">No recommendations available.</div>`
                }
            </div>

            <div class="row g-3">
                <div class="col-lg-6">
                    <div class="panel-card h-100">
                        <div class="panel-title">Context Notes</div>
                        ${
                            contextNotes.length
                                ? `<ul class="list-clean">
                                    ${contextNotes.map(note => `<li><strong>${escapeHtml(note.noteType || "Note")}:</strong> ${escapeHtml(note.message || "-")}</li>`).join("")}
                                   </ul>`
                                : `<div class="small-muted">No context notes available.</div>`
                        }
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="panel-card h-100">
                        <div class="panel-title">Data Completeness Notes</div>
                        ${notesHtml(data.dataCompletenessNotes)}
                    </div>
                </div>
            </div>
        </div>
    `;
}

function showError(err) {
    const payload = err?.payload || { error: err.message || "Something went wrong" };
    prettyJson(payload);
}

function setDefaultValues() {
    document.getElementById("createdAt").value = localInputValue();
    document.getElementById("startDateTime").value = hoursOffsetValue(-24);
    document.getElementById("endDateTime").value = localInputValue();
    document.getElementById("reportCompletedAt").value = localInputValue();
}

function fillSample() {
    document.getElementById("requestId").value = "REQ-001";
    document.getElementById("propertyId").value = "PROP-101";
    document.getElementById("location").value = "Leicester";
    document.getElementById("propertyType").value = "Home";
    document.getElementById("granularity").value = "Hourly";
    document.getElementById("testMode").value = "";
    setDefaultValues();
}

window.useReportId = function(reportId) {
    document.getElementById("reportLookupId").value = reportId || "";
};

window.copyAssessmentId = function(assessmentId) {
    copyToClipboard(assessmentId || "");
};

window.copyReportId = function(reportId) {
    copyToClipboard(reportId || "");
};

window.useAssessmentForReportPayload = function() {
    const data = window.__latestAssessment;
    if (!data) return;

    document.getElementById("reportAssessmentId").value = data.assessmentId || "";
    document.getElementById("assessmentLookupId").value = data.assessmentId || "";
    document.getElementById("reportAssessmentStatus").value = data.assessmentStatus || "Completed";
    document.getElementById("reportCompletedAt").value = isoToLocalInput(data.completedAt);
};

document.getElementById("fillSampleBtn").addEventListener("click", fillSample);

document.getElementById("clearOutputBtn").addEventListener("click", () => {
    output.textContent = `{
  "message": "Responses cleared"
}`;
    assessmentSummary.innerHTML = "";
    reportCreateSummary.innerHTML = "";
    reportSummary.innerHTML = "";
});

document.getElementById("assessmentForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
        requestId: document.getElementById("requestId").value.trim(),
        createdAt: toIso(document.getElementById("createdAt").value),
        property: {
            propertyId: document.getElementById("propertyId").value.trim(),
            location: document.getElementById("location").value.trim(),
            propertyType: document.getElementById("propertyType").value
        },
        timeWindow: {
            startDateTime: toIso(document.getElementById("startDateTime").value),
            endDateTime: toIso(document.getElementById("endDateTime").value),
            granularity: document.getElementById("granularity").value
        }
    };

    const testMode = document.getElementById("testMode").value;
    if (testMode) {
        payload.testMode = testMode;
    }

    try {
        const data = await apiFetch(`${API_BASE}/assessments`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        prettyJson(data);
        renderAssessmentSummary(data);
        window.useAssessmentForReportPayload();
    } catch (err) {
        showError(err);
    }
});

document.getElementById("fetchAssessmentBtn").addEventListener("click", async () => {
    const assessmentId = document.getElementById("assessmentLookupId").value.trim();
    if (!assessmentId) return;

    try {
        const data = await apiFetch(`${API_BASE}/assessments/${assessmentId}`);
        prettyJson(data);
        renderAssessmentSummary(data);
        window.useAssessmentForReportPayload();
    } catch (err) {
        showError(err);
    }
});

document.getElementById("reportForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
        assessmentId: document.getElementById("reportAssessmentId").value.trim(),
        assessmentStatus: document.getElementById("reportAssessmentStatus").value,
        completedAt: toIso(document.getElementById("reportCompletedAt").value)
    };

    try {
        const data = await apiFetch(`${API_BASE}/reports`, {
            method: "POST",
            body: JSON.stringify(payload)
        });

        prettyJson(data);
        renderReportCreateSummary(data);

        if (data.reportId) {
            document.getElementById("reportLookupId").value = data.reportId;
        }
    } catch (err) {
        showError(err);
    }
});

document.getElementById("fetchReportBtn").addEventListener("click", async () => {
    const reportId = document.getElementById("reportLookupId").value.trim();
    if (!reportId) return;

    try {
        const data = await apiFetch(`${API_BASE}/reports/${reportId}`);
        prettyJson(data);
        renderReportSummary(data);
    } catch (err) {
        showError(err);
    }
});

setDefaultValues();