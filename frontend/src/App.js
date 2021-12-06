import './App.css';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
// Import Views
import Home from "./views/Home"
import Student from "./views/Student"

function App() {
  return (
    <>
      <Navbar/>
      <Router>
        <Routes>
        < Route path="/" element={<Home />} />
        < Route path="/student" element={<Student />} />
        < Route path="*" element={<div>Nothing's here~</div>} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
