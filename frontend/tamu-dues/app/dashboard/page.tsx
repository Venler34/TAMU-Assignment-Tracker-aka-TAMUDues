"use client"

import {useEffect, useState} from "react";
import Navbar from "@/components/Navbar";
import Calendar from "@/components/Calendar";
import PriorityList from "@/components/PriorityList";
import AssignmentPopup from "@/components/AssignmentPopup";
import { Assignment } from "@/types/assignment";
import { useRouter } from "next/navigation";

import AddAssignmentPopup from "@/components/AddAssignmentPopup";
import EditAssignmentPopup from "@/components/EditAssignmentPopup";

import { AddAssignment, DeleteAssignment, EditAssignment } from "@/lib/account";


export default function Dashboard() {
    const [name, setName] = useState("Anonymous");
    const router = useRouter();

    const [showAddPopup, setShowAddPopup] = useState(false);
    const [showEditPopup, setShowEditPopup] = useState(false);

    const [assignments, setAssignments] = useState<Assignment[]>([]);
    const [selectedAssignment, setSelectedAssignment] = useState<Assignment | undefined>();

    const handleAssignmentClick = (assignment: Assignment) => setSelectedAssignment(assignment);
    const handleClosePopup = () => setSelectedAssignment(undefined);

    const handleAddAssignment = () => setShowAddPopup(true);

    const handleSaveAssignment = async (newAssignment: Assignment) => {
        // Send API Request to save assignment
        const assignment = await AddAssignment(newAssignment); // Error Handling?
        setAssignments([...assignments, assignment]);
    };

    const handleEdit = (assignment: Assignment) => {
        setSelectedAssignment(assignment);
        setShowEditPopup(true);
    };

    const handleSaveUpdatedAssignment = async (assignment: Assignment) => {
        const updatedAssignment = await EditAssignment(assignment); // Error Handling?
        const withoutOldAssignment = assignments.filter(a => a.id !== assignment.id);
        setAssignments([...withoutOldAssignment, updatedAssignment]);
        setSelectedAssignment(updatedAssignment);
    }

    const handleDelete = (id: string) => {
        setAssignments(assignments.filter(a => a.id !== id));
        DeleteAssignment(id);
        handleClosePopup();
    };

    const handleToggleComplete = (id: string) => {
        setAssignments(assignments.map(a =>
        a.id === id ? { ...a, status: a.status === "COMPLETE" ? "INCOMPLETE" : "COMPLETE" } : a
        ));
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
        {
            showAddPopup && (
            <AddAssignmentPopup
                onClose={() => setShowAddPopup(false)}
                onAdd={handleSaveAssignment}
            />)
        }

        {showEditPopup && selectedAssignment && (
            <EditAssignmentPopup
                assignment={selectedAssignment}
                onClose={() => setShowEditPopup(false)}
                onSave={handleSaveUpdatedAssignment}
            />
            )}
        
        </div>
    );
}
