"use client";

import {useState} from "react";
import { useRouter } from "next/navigation";

export default function Signin() {
    const router = useRouter();
    const loginURL = "http://localhost:8080/v1/auth/login";
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");

        try {
            const res = await fetch(loginURL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ "username": username, "password": password }),
            });

            console.log("Running with")


            if (!res.ok) {
                const err = await res.json();
                setError(err.message || "Login failed");
                return;
            }

            const data = await res.json();
            console.log("Token ", data.token);
            localStorage.setItem("authToken", data.token); // store jwt token

            router.push("/dashboard");
        } 
        catch (err) {
            console.error(err);
            setError("Something went wrong. Please try again.");
        }
  };

    return (
        <div className="signInForm">
            <form className="flex flex-col text-center" onSubmit={handleSubmit}>
                <h2>Sign In</h2>

                <input id="username"
                    placeholder="Username"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required />

                <input id="password"
                    placeholder="Password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required />

                <button type="submit">Sign In</button>
                {error && <p>{error}</p>}
            </form>
        </div>
    )
}