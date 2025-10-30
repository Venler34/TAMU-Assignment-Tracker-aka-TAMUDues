"use client";
import { useState } from "react";
import { Assignment } from "@/types/assignment";

interface AddAssignmentPopupProps {
  onClose: () => void;
  onAdd: (assignment: Assignment) => void;
}

export default function AddAssignmentPopup({ onClose, onAdd }: AddAssignmentPopupProps) {
  const [name, setName] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [description, setDescription] = useState("");
  const [priority, setPriority] = useState<"HIGH" | "MEDIUM" | "LOW">("MEDIUM");

  const handleSubmit = () => {
    if (!name || !dueDate) return alert("Please fill out all required fields.");

    const newAssignment: Assignment = {
      name,
      dueDate,
      status: "INCOMPLETE",
      description,
      priority,
    };

    onAdd(newAssignment);
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
      <div className="bg-green-500 text-white p-6 rounded-lg w-96 relative">
        <button onClick={onClose} className="absolute top-3 right-3 font-bold">✕</button>
        <h2 className="text-xl font-semibold mb-4">Add Assignment</h2>

        <label className="block mb-2">
          Name:
          <input
            type="text"
            value={name}
            onChange={e => setName(e.target.value)}
            className="w-full text-black p-1 rounded mt-1"
            placeholder="Enter assignment name"
          />
        </label>

        <label className="block mb-2">
          Due Date:
          <input
            type="datetime-local"
            value={dueDate}
            onChange={e => setDueDate(e.target.value)}
            className="border border-gray-300 rounded-lg p-2 focus:ring-2 focus:ring-blue-500 focus:outline-none"
          />
        </label>

        <label className="block mb-2">
          Description:
          <textarea
            value={description}
            onChange={e => setDescription(e.target.value)}
            className="w-full text-black p-1 rounded mt-1"
            placeholder="Short description..."
          />
        </label>

        <label className="block mb-4">
          Priority:
          <select
            value={priority}
            onChange={e => setPriority(e.target.value as "HIGH" | "MEDIUM" | "LOW")}
            className="w-full text-black p-1 rounded mt-1"
          >
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
          </select>
        </label>

        <div className="flex justify-end">
          <button
            onClick={handleSubmit}
            className="bg-green-700 px-4 py-2 rounded hover:bg-green-600"
          >
            Add
          </button>
        </div>
      </div>
    </div>
  );
}
