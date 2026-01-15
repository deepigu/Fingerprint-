// Employees stored in localStorage
let employees = JSON.parse(localStorage.getItem("employees") || "[]");

// Modal functions
function showRegister() { document.getElementById("registerModal").style.display = "block"; }
function showAuthenticate() { document.getElementById("authModal").style.display = "block"; }
function closeModal(modalId) { document.getElementById(modalId).style.display = "none"; }

// Register Employee
function registerEmployee() {
    let id = document.getElementById("regId").value.trim();
    let name = document.getElementById("regName").value.trim();
    let fingerprint = document.getElementById("regFinger").value.trim();

    if(id && name && fingerprint){
        employees.push({id, name, fingerprint});
        localStorage.setItem("employees", JSON.stringify(employees));
        alert("✅ Employee Registered Successfully");

        // Clear inputs
        document.getElementById("regId").value = "";
        document.getElementById("regName").value = "";
        document.getElementById("regFinger").value = "";
    } else {
        alert("❌ Please fill all fields");
    }
}

// Authenticate Employee
function authenticateEmployee() {
    let id = document.getElementById("authId").value.trim();
    let fingerprint = document.getElementById("authFinger").value.trim();

    let emp = employees.find(e => e.id === id && e.fingerprint.toLowerCase() === fingerprint.toLowerCase());
    if(emp) alert("✅ ACCESS GRANTED");
    else alert("❌ ACCESS DENIED");

    // Clear inputs
    document.getElementById("authId").value = "";
    document.getElementById("authFinger").value = "";
}

// List Employees
function listEmployees() {
    if(employees.length === 0){
        document.getElementById("output").innerText = "No employees registered";
        return;
    }
    let text = "Registered Employees:\n";
    employees.forEach(e => text += `ID: ${e.id}, Name: ${e.name}\n`);
    document.getElementById("output").innerText = text;
}

// Total Employees
function checkTotal() {
    alert("Total Employees: " + employees.length);
}

// Close modal when clicking outside
window.onclick = function(event){
    if(event.target.className === "modal") event.target.style.display = "none";
}
