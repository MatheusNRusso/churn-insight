const $ = (id) => document.getElementById(id);

const STORAGE_KEY = "churninsight.jwt.token";

// Predict defaults
const DEFAULT_PREDICT_PATH = "/api/predict";
const DEFAULT_CONTRACT_EXAMPLE = {
    gender: "MALE",
    seniorCitizen: true,
    partner: true,
    dependents: true,
    contractMonths: 72,
    phoneService: true,
    multipleLines: "YES",
    internetService: "DSL",
    onlineSecurity: "YES",
    onlineBackup: "YES",
    deviceProtection: "YES",
    techSupport: "YES",
    streamingTV: "YES",
    streamingMovies: "YES",
    contractType: "MONTH_TO_MONTH",
    paperlessBilling: true,
    paymentMethod: "ELECTRONIC_CHECK",
    monthlyCharges: 200,
    totalCharges: 10000
};

function apiUrl(path) {
    const base = $("baseUrl").value.trim();
    if (!base) return path; // same origin
    return base.replace(/\/+$/, "") + path;
}

function setOut(obj) {
    $("out").textContent =
        (typeof obj === "string") ? obj : JSON.stringify(obj, null, 2);
}

function saveToken(token) {
    if (!token) return;
    localStorage.setItem(STORAGE_KEY, token);
    $("token").value = token;
}

function loadToken() {
    return localStorage.getItem(STORAGE_KEY) || "";
}

function authHeader() {
    const t = $("token").value.trim();
    if (!t) return {};
    return { "Authorization": `Bearer ${t}` };
}

async function request(method, path, bodyObj) {
    const headers = { "Content-Type": "application/json", ...authHeader() };
    const opts = { method, headers };

    if (bodyObj !== undefined && bodyObj !== null && method !== "GET") {
        opts.body = JSON.stringify(bodyObj);
    }

    const res = await fetch(apiUrl(path), opts);
    const text = await res.text();

    let payload;
    try { payload = text ? JSON.parse(text) : {}; }
    catch { payload = text; }

    return { ok: res.ok, status: res.status, payload };
}

function setupTabs() {
    document.querySelectorAll(".tab").forEach(t => {
        t.addEventListener("click", () => {
            document.querySelectorAll(".tab").forEach(x => x.classList.remove("active"));
            t.classList.add("active");

            const name = t.dataset.tab;
            ["register", "login", "predict", "stats"].forEach(n => {
                $("tab-" + n).classList.toggle("hidden", n !== name);
            });
        });
    });
}

function setupActions() {
    // init token
    $("token").value = loadToken();

    $("btnClear").addEventListener("click", () => {
        localStorage.removeItem(STORAGE_KEY);
        $("token").value = "";
        setOut({ message: "Token removido." });
    });

    $("btnPasteToken").addEventListener("click", () => {
        $("token").value = loadToken();
        setOut({ message: "Token carregado do localStorage." });
    });

    // register
    $("btnRegister").addEventListener("click", async () => {
        const body = {
            email: $("regEmail").value.trim(),
            password: $("regPass").value,
            name: $("regName").value.trim()
        };
        const r = await request("POST", "/api/auth/register", body);
        setOut(r);
    });

    // login
    $("btnLogin").addEventListener("click", async () => {
        const body = {
            email: $("logEmail").value.trim(),
            password: $("logPass").value
        };
        const r = await request("POST", "/api/auth/login", body);
        if (r.ok && r.payload && r.payload.token) {
            saveToken(r.payload.token);
        }
        setOut(r);
    });

    // Predict init
    $("predictPath").value = DEFAULT_PREDICT_PATH;
    $("predictBody").value = JSON.stringify(DEFAULT_CONTRACT_EXAMPLE, null, 2);

    // Swagger-like labels: Server + Path
    const updateServerLabel = () => {
        const base = $("baseUrl").value.trim() || "http://localhost:8080";
        const el = document.getElementById("serverLabel");
        if (el) el.textContent = base;
    };

    $("baseUrl").addEventListener("input", updateServerLabel);
    updateServerLabel();

    const pathLabel = document.getElementById("predictPathLabel");
    if (pathLabel) pathLabel.textContent = DEFAULT_PREDICT_PATH;

    $("btnFillPredict").addEventListener("click", () => {
        $("predictBody").value = JSON.stringify(DEFAULT_CONTRACT_EXAMPLE, null, 2);
        $("predictPath").value = DEFAULT_PREDICT_PATH;

        const pathLabel2 = document.getElementById("predictPathLabel");
        if (pathLabel2) pathLabel2.textContent = DEFAULT_PREDICT_PATH;

        setOut({ message: "Exemplo preenchido para /api/predict." });
    });

    $("btnPredict").addEventListener("click", async () => {
        const pathRaw = $("predictPath").value.trim() || DEFAULT_PREDICT_PATH;
        const path = pathRaw.startsWith("/") ? pathRaw : "/" + pathRaw;

        // mantém label sincronizado
        const pathLabel3 = document.getElementById("predictPathLabel");
        if (pathLabel3) pathLabel3.textContent = path;

        const raw = $("predictBody").value.trim();
        let body;

        try {
            body = JSON.parse(raw);
        } catch {
            return setOut({ ok: false, status: 0, payload: "Body JSON inválido (Predict)." });
        }

        const r = await request("POST", path, body);
        setOut(r);
    });

    // stats
    $("btnStats").addEventListener("click", async () => {
        const r = await request("GET", "/api/stats");
        setOut(r);
    });

    $("btnHealth").addEventListener("click", async () => {
        const r = await request("GET", "/actuator/health");
        setOut(r);
    });
}

setupTabs();
setupActions();
setOut({ message: "Pronto. Siga o fluxo: Register → Login → Predict → Stats." });
