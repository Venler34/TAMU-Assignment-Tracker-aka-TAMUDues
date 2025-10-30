interface NavbarProps {
  onClick: () => void;
  name: string;
}

export default function Navbar({ onClick, name }: NavbarProps) {
  return (
    <nav className="w-full bg-green-400 flex justify-between items-center px-6 py-3">
      <div className="bg-green-600 w-10 h-10"></div>
      <button
        onClick={onClick}
        className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 transition"
      >
        {name}
      </button>
    </nav>
  );
}
