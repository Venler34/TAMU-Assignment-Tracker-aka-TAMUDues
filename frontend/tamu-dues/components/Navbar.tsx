interface NavbarProps {
  onClick: () => void;
  name: string;
}

import Image from "next/image";

export default function Navbar({ onClick, name }: NavbarProps) {
  return (
    <nav className="w-full bg-green-400 flex justify-between items-center px-6 py-2">
      <Image src="/Logo1.png" alt="Logo" width={100} height={100}/>
      <button
        onClick={onClick}
        className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 transition"
      >
        {name}
      </button>
    </nav>
  );
}
