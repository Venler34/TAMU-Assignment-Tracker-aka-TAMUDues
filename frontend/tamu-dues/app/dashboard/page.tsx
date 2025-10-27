"use client"

import {useEffect, useState} from "react";

export default function Dashboard() {
    const [name, setName] = useState("Anonymous");

    useEffect(() => {
        const fetchData = async () => {
            // may refactor so includes id of person
            const url = "http://localhost:8080/v1/users/8";
            const token = localStorage.getItem("authToken");
            const res = await fetch(url, {
                method: "GET",
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            });

            const data = await res.json();
            setName(data.username);
        };
        
        fetchData();
    }, [])

    return (
        <h1>Hi {name}!</h1>
    );
}