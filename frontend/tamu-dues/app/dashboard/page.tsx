"use client"

import {useEffect, useState} from "react";
import Navbar from "@/components/Navbar";
import Calendar from "@/components/Calendar";
import PriorityList from "@/components/PriorityList";
import AssignmentPopup from "@/components/AssignmentPopup";
import { Assignment } from "@/types/assignment";
import { useRouter } from "next/navigation";


export default function Dashboard() {
    const [name, setName] = useState("Anonymous");
    const router = useRouter();


    const [assignments, setAssignments] = useState<Assignment[]>([]);
    const [selectedAssignment, setSelectedAssignment] = useState<Assignment | undefined>();

    const handleAssignmentClick = (assignment: Assignment) => setSelectedAssignment(assignment);
    const handleClosePopup = () => setSelectedAssignment(undefined);

    const handleDelete = (id: string) => {
        setAssignments(assignments.filter(a => a.id !== id));
        handleClosePopup();
    };

    const handleToggleComplete = (id: string) => {
        setAssignments(assignments.map(a =>
        a.id === id ? { ...a, status: a.status === "complete" ? "incomplete" : "complete" } : a
        ));
    };

    const handleEdit = (assignment: Assignment) => {
        // Implement edit popup or inline form here
    };

    const handleAddAssignment = () => {
        // Open "new assignment" popup
    };

    const signout = () => {
        localStorage.removeItem("authToken");
        router.push("/");
    }

    useEffect(() => {
        const fetchData = async () => {
            const url = "http://localhost:8080/v1/users";
            const token = localStorage.getItem("authToken");
            const res = await fetch(url, {
                method: "GET",
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            });

            const data = await res.json();
            setName(data.username);
            setAssignments(data.assignments);
        };
        // const assignment: Assignment = { For Testing Assignment
        //     id: "1",
        //     name: "Differential Equations",
        //     dueDate: "10-8-2025",
        //     description: "HW",
        //     priority: "high",
        //     status: "complete"
        // };
        // setSelectedAssignment(assignment)
        
        fetchData();
    }, [])

    return (
        <div className="min-h-screen bg-green-300 flex flex-col">
        <Navbar onClick={signout} name="Sign Out"/>
        <div className="text-4xl text-white px-8 py-2">
            {`Hi ${name}`}
        </div>
        <div className="flex flex-1 p-6 gap-4">
            <Calendar
            assignments={assignments}
            onAssignmentClick={handleAssignmentClick}
            onAddAssignment={handleAddAssignment}
            />
            <PriorityList
            assignments={assignments}
            onAssignmentClick={handleAssignmentClick}
            />
        </div>

        <AssignmentPopup
            assignment={selectedAssignment}
            onClose={handleClosePopup}
            onEdit={handleEdit}
            onDelete={handleDelete}
            onToggleComplete={handleToggleComplete}
        />
        </div>
    );
}
