"use client";
import { Assignment } from "@/types/assignment";

interface PriorityListProps {
  assignments: Assignment[];
  onAssignmentClick: (assignment: Assignment) => void;
}

export default function PriorityList({ assignments, onAssignmentClick }: PriorityListProps) {
  const getColor = (priority: string) => {
    if (priority === "HIGH") return "bg-red-400";
    if (priority === "MEDIUM") return "bg-yellow-300";
    return "bg-green-300";
  };

  const getPriorityEffect = (a: Assignment): number => {
    if(a.priority == "HIGH") {
      return 2 * 8.64 * 10 ** 7; // -2 days
    } else if(a.priority == "MEDIUM") {
      return 1 * 8.64 * 10 ** 7; // -1 day
    } else {
      return 0;
    }
  }

  const compareAssignments = (a: Assignment, b: Assignment) => {
    // return < 0 if assignment a has higher priority than assignment b (a comes before b)
    let assignmentADueDate = new Date(a.dueDate).getTime(); // in milliseconds
    let assignmentBDueDate = new Date(b.dueDate).getTime();

    assignmentADueDate -= getPriorityEffect(a);
    assignmentBDueDate -= getPriorityEffect(b);

    // if high priority we subtract from due date (in effect making it look like its due earlier)
    // Later on could add logic for how much the assignment has been completed
    console.log(assignmentADueDate - assignmentBDueDate)
    console.log(a.name, b.name);
    return assignmentADueDate - assignmentBDueDate; // < 0 if assignmentA is due before assignment B
  }

  return (
    <div className="bg-green-700 text-white p-4 rounded-lg w-1/4">
      <h2 className="text-xl mb-3 text-center">Prioritize</h2>
      <div className="flex flex-col gap-2 overflow-y-auto">
        {assignments.filter(a => a.status === "INCOMPLETE").sort(compareAssignments).map(a => (
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
