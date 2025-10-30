"use client";
import { useState, useEffect } from "react";
import { Assignment } from "@/types/assignment";

interface EditAssignmentPopupProps {
  assignment: Assignment;
  onClose: () => void;
  onSave: (updated: Assignment) => void;
}

export default function EditAssignmentPopup({
  assignment,
  onClose,
  onSave,
}: EditAssignmentPopupProps) {
  const [name, setName] = useState(assignment.name);
  const [dueDate, setDueDate] = useState(assignment.dueDate);
  const [description, setDescription] = useState(assignment.description || "");
  const [priority, setPriority] = useState<"HIGH" | "MEDIUM" | "LOW">(assignment.priority);

  useEffect(() => {
    setName(assignment.name);
    setDueDate(assignment.dueDate);
    setDescription(assignment.description || "");
    setPriority(assignment.priority);
  }, [assignment]);

  const handleSave = () => {
    if (!name || !dueDate) return alert("Please fill out all required fields.");

    const updatedAssignment: Assignment = {
      ...assignment,
      name,
      dueDate,
      description,
      priority,
    };

    onSave(updatedAssignment);
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
      <div className="bg-green-500 text-white p-6 rounded-lg w-96 relative">
        <button onClick={onClose} className="absolute top-3 right-3 font-bold">✕</button>
        <h2 className="text-xl font-semibold mb-4">Edit Assignment</h2>

        <label className="block mb-2">
          Name:
          <input
            type="text"
            value={name}
            onChange={e => setName(e.target.value)}
            className="w-full text-black p-1 rounded mt-1"
          />
        </label>

        <label className="block mb-2">
          Due Date:
          <input
            type="datetime-local"
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
            onClick={handleSave}
            className="bg-blue-500 px-4 py-2 rounded hover:bg-blue-400"
          >
            Save
          </button>
        </div>
      </div>
    </div>
  );
}
