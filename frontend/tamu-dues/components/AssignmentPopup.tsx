"use client";
import { Assignment } from "@/types/assignment";

interface PopupProps {
  assignment?: Assignment;
  onClose: () => void;
  onEdit: (assignment: Assignment) => void;
  onDelete: (id: string) => void;
  onToggleComplete: (id: string, currStatus: string) => void;
}

export default function AssignmentPopup({
  assignment,
  onClose,
  onEdit,
  onDelete,
  onToggleComplete,
}: PopupProps) {
  if (!assignment) return null;

  const customizeDate = (dateStr: string) => {
    const date = new Date(dateStr)
    const options: Intl.DateTimeFormatOptions = { 
      year: "numeric", 
      month: "long", 
      day: "numeric", 
      hour: "2-digit", 
      minute: "2-digit",
      timeZoneName: "short"
    };

    // Format and replace text content
    return date.toLocaleString("en-US", options);
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
      <div className="bg-green-500 text-white p-6 rounded-lg w-96 relative">
        <button onClick={onClose} className="absolute top-3 right-3 font-bold">✕</button>
        <h2 className="text-xl font-semibold">{assignment.name}</h2>
        <p>Due: {customizeDate(assignment.dueDate)}</p>
        <p>Status: {assignment.status}</p>
        <p>Priority: {assignment.priority}</p>

        <hr className="my-2"></hr>

        <p>{assignment.description}</p>

        <div className="flex justify-between mt-4">
          <button
            onClick={() => onToggleComplete(assignment.id!, assignment.status)}
            className="bg-green-700 px-3 py-1 rounded"
          >
            {assignment.status === "COMPLETE" ? "Mark Incomplete" : "Complete"}
          </button>
          <button
            onClick={() => onEdit(assignment)}
            className="bg-blue-400 px-3 py-1 rounded"
          >
            Edit
          </button>
          <button
            onClick={() => onDelete(assignment.id!)}
            className="bg-red-500 px-3 py-1 rounded"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
}
