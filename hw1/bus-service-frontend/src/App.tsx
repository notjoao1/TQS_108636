import Content from "./components/Content";
import Navbar from "./components/Navbar";

function App() {
  return (
    <div className="flex flex-col">
      <Navbar />
      <div className="ml-52 mr-52 mt-10 mb-10">
        <Content />
      </div>
    </div>
  );
}

export default App;
