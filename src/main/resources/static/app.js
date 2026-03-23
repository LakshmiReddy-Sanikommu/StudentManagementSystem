// Switching tabs
function switchTab(type) {
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    event.target.classList.add('active');
    document.getElementById('loginType').value = type;
    document.getElementById('regType').value = type;
    
    // Toggle fields
    if (type === 'student') {
        document.getElementById('studentFields').style.display = 'block';
        document.getElementById('lecturerFields').style.display = 'none';
    } else {
        document.getElementById('studentFields').style.display = 'none';
        document.getElementById('lecturerFields').style.display = 'block';
    }
}

function toggleRegister() {
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');
    if (loginForm.style.display === 'none') {
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
        document.querySelector('h1').innerText = 'Welcome Back';
    } else {
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
        document.querySelector('h1').innerText = 'Create Account';
    }
}

function showNotification(msg, isError = false) {
    const notif = document.getElementById('notification');
    notif.innerText = msg;
    notif.style.background = isError ? '#ef4444' : '#10b981';
    notif.style.color = '#fff';
    notif.classList.add('show');
    setTimeout(() => notif.classList.remove('show'), 3000);
}

async function handleLogin(e) {
    e.preventDefault();
    const type = document.getElementById('loginType').value;
    const body = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    try {
        const res = await fetch(`/api/${type}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        
        if (!res.ok) throw new Error('Invalid credentials');
        
        const data = await res.json();
        localStorage.setItem('user', JSON.stringify(data));
        localStorage.setItem('userType', type);
        
        showNotification('Login successful!');
        setTimeout(() => {
            window.location.href = `/${type}_dashboard.html`;
        }, 800);
    } catch (err) {
        showNotification(err.message, true);
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const type = document.getElementById('regType').value;
    
    let body = {
        username: document.getElementById('regUsername').value,
        password: document.getElementById('regPassword').value
    };

    if (type === 'student') {
        body.section = document.getElementById('regSection').value;
        body.id = parseInt(document.getElementById('regId').value || 0);
    } else {
        body.lecturerId = parseInt(document.getElementById('regLocId').value || 0);
    }

    try {
        const res = await fetch(`/api/${type}/add`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        
        if (!res.ok) throw new Error('Registration failed');
        
        showNotification('Registration successful! Please login.');
        toggleRegister();
    } catch (err) {
        showNotification(err.message, true);
    }
}

function logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('userType');
    window.location.href = '/index.html';
}

async function handleAssignmentAction(action) {
    try {
        const res = await fetch(`/api/assignment/${action}`, { method: 'POST' });
        const text = await res.text();
        showNotification(text);
    } catch (err) {
        showNotification('Action failed', true);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    // Check auth on dashboard
    if (window.location.pathname.includes('dashboard')) {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) {
            window.location.href = '/index.html';
            return;
        }
        const nameDisplay = document.getElementById('userNameDisplay');
        if (nameDisplay) nameDisplay.innerText = user.username;
    }
});
