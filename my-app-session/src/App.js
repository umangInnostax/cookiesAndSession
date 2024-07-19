import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Home } from './components/home/Home';
import { AddInfo } from './components/addInfo/AddInfo';
// import { EditInfo } from './components/editInfo/EditInfo';
import { useEffect } from 'react';

function App() {

  useEffect(() => {
    fetch('http://localhost:8080/practiceCrud/getInfo')
        .then(response => response.json())
        .then(json =>{
          sessionStorage.setItem('users', JSON.stringify(json));
          sessionStorage.setItem('newUser', JSON.stringify({name:"", address:"", position:"", mobileNo:""}))
        })
        .catch(error => console.error(error));
  })
  
  return(
    <BrowserRouter> 
      <Routes>
        <Route path='/' element={<Home />}/>
        <Route path='/addNewUser' element={<AddInfo />}/>
        { /* <Route path='/editUser' element={<EditInfo />}/> */}
      </Routes>
    </BrowserRouter>
  )
}

export default App;
