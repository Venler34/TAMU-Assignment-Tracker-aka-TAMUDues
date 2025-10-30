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
  const [priority, setPriority] = useState<"high" | "medium" | "low">("medium");

  const handleSubmit = () => {
    if (!name || !dueDate) return alert("Please fill out all required fields.");

    const newAssignment: Assignment = {
      id: crypto.randomUUID(),
      name,
      dueDate,
      status: "incomplete",
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
            type="date"
            value={dueDate}
            onChange={e => setDueDate(e.target.value)}
            className="w-full text-black p-1 rounded mt-1"
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
            onChange={e => setPriority(e.target.value as "high" | "medium" | "low")}
            className="w-full text-black p-1 rounded mt-1"
          >
            <option value="high">High</option>
            <option value="medium">Medium</option>
            <option value="low">Low</option>
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
