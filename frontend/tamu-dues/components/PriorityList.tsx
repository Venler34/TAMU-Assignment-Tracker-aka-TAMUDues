"use client";
import { Assignment } from "@/types/assignment";

interface PriorityListProps {
  assignments: Assignment[];
  onAssignmentClick: (assignment: Assignment) => void;
}

export default function PriorityList({ assignments, onAssignmentClick }: PriorityListProps) {
  const getColor = (priority: string) => {
    if (priority === "high") return "bg-red-400";
    if (priority === "medium") return "bg-yellow-300";
    return "bg-green-300";
  };

  return (
    <div className="bg-green-700 text-white p-4 rounded-lg w-1/4">
      <h2 className="text-xl mb-3 text-center">Prioritize</h2>
      <div className="flex flex-col gap-2">
        {assignments.map(a => (
          <button
            key={a.id}
            onClick={() => onAssignmentClick(a)}
            className={`${getColor(a.priority)} p-2 rounded text-left`}
          >
            {a.name}
          </button>
        ))}
      </div>
    </div>
  );
}
