import { Assignment } from "@/types/assignment";

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

export async function AddAssignment(assignment: Assignment) {
    try {
        const res = await fetch(`${apiBase}/v1/users/assignments`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("authToken")}`
            },
            body: JSON.stringify(assignment)
        });

        const returnedAssignment = res.json();
        return returnedAssignment; // assignmnet successfully created
    } catch (error) {
        return false;
    }
}

export async function DeleteAssignment(assignmentId: String) {
    try {
        const res = await fetch(`${apiBase}/v1/users/assignments/${assignmentId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("authToken")}`
            },
        });

        return res.ok; // assignment successfully created
    } catch (error) {
        return false;
    }
}

export async function EditAssignment(assignment: Assignment) {
    try {
        const res = await fetch(`${apiBase}/v1/users/assignments/${assignment.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("authToken")}`
            },
            body: JSON.stringify(assignment)
        });

        const returnedAssignment = res.json();
        return returnedAssignment; // assignment updated successfully
    } catch (error) {
        return false;
    }
}