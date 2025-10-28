const apiBase = process.env.NEXT_PUBLIC_API_BASE_URL;

export async function SignIn(username: String, password: String) {
    try {
        const res = await fetch(`${apiBase}/v1/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ "username": username, "password": password }),
        });

        const data = await res.json();
        localStorage.setItem("authToken", data.token); // store jwt token

        return true;
    } catch (error) {
        return false;
    }
}

export async function Register(username: String, password: String) {
    try {
        const res = await fetch(`${apiBase}/v1/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ "username": username, "password": password }),
        });

        const data = await res.json();


        return true;
    } catch (error) {
        return false;
    }
}