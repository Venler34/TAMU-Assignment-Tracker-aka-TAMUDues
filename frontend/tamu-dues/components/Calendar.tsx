"use client";
import { useState } from "react";
import { Assignment } from "@/types/assignment";

interface CalendarProps {
  assignments: Assignment[];
  onAssignmentClick: (assignment: Assignment) => void;
  onAddAssignment: () => void;
}

export default function Calendar({ assignments, onAssignmentClick, onAddAssignment }: CalendarProps) {
  const [currentDate, setCurrentDate] = useState(new Date());

  const handleMonthChange = (offset: number) => {
    const newDate = new Date(currentDate);
    newDate.setMonth(currentDate.getMonth() + offset);
    setCurrentDate(newDate);
  };

  const daysInMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0).getDate();
  const startDay = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1).getDay();
  const weekdays = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

  const days = Array.from({ length: startDay + daysInMonth }, (_, i) =>
    i < startDay ? null : i - startDay + 1
  );

  return (
    <div className="bg-green-600 text-white p-4 rounded-lg flex-1">
      <div className="flex justify-between items-center mb-3">
        <div className="flex gap-2">
          <button onClick={() => handleMonthChange(-1)}>◀</button>
          <span className="text-lg font-semibold">
            {currentDate.toLocaleString("default", { month: "long" })} {currentDate.getFullYear()}
          </span>
          <button onClick={() => handleMonthChange(1)}>▶</button>
        </div>
        <button onClick={onAddAssignment} className="bg-green-500 px-3 py-1 rounded hover:bg-green-400">+</button>
      </div>

      <div className="grid grid-cols-7 text-center font-bold">
        {weekdays.map(day => <div key={day}>{day}</div>)}
      </div>

      <div className="grid grid-cols-7 gap-1 mt-2">
        {days.map((day, i) => (
          <div
            key={i}
            className="bg-gray-100 text-black h-16 rounded p-1 relative"
          >
            {day && <span className="text-sm font-semibold">{day}</span>}
            {day && assignments
              .filter(a => new Date(a.dueDate).getDate() === day)
              .map(a => (
                <button
                  key={a.id}
                  onClick={() => onAssignmentClick(a)}
                  className="block bg-green-400 text-xs mt-1 w-full rounded"
                >
                  {a.name}
                </button>
              ))}
          </div>
        ))}
      </div>
    </div>
  );
}
