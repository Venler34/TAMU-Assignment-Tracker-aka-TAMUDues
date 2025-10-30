export interface Assignment {
    id: string;
    name: string;
    dueDate: string; // ISO date
    status: "complete" | "incomplete";
    description?: string;
    priority: "high" | "medium" | "low";
}