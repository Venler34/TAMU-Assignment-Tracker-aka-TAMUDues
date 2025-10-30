export interface Assignment {
    id?: string;
    name: string;
    dueDate: string; // ISO date
    status: "COMPLETE" | "INCOMPLETE";
    description?: string;
    priority: "HIGH" | "MEDIUM" | "LOW";
}