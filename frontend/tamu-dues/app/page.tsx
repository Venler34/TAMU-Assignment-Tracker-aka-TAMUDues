import Navbar from "./components/Navbar";

export default function Home() {
  return (
    <div className="HomePage">
      <div>
        <Navbar />
      </div>
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-4xl">
        <h1>TAMUDues</h1>
      </div>
    </div>
  );
}
