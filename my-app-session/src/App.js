import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Home } from './components/home/Home';
import { AddInfo } from './components/addInfo/AddInfo';
import { useCookies } from 'react-cookie';
import { EditInfo } from './components/editInfo/EditInfo';
// import Cookies from 'universal-cookie';
import { useEffect } from 'react';

function App() {

const [, setCookie] = useCookies(['addUserButtonClickCountFrontend']);


  useEffect(() => {
    async function fetchData() {
      await fetch('http://localhost:8080/practiceCrud/getInfo', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include' // Include cookies
      })
      .then(response => response.json())
      .then(json =>{
        // console.log(getCookie("addUserButtonClickCount"));
        sessionStorage.setItem('users', JSON.stringify(json));
        sessionStorage.setItem('newUser', JSON.stringify({name:"", address:"", position:"", mobileNo:""}))
        sessionStorage.setItem('editUser', JSON.stringify({userId:-1, name:"", address:"", position:"", mobileNo:""}))
        setCookie('addUserButtonClickCountFrontend', 0, { path: '/' });
        setCookie('editUserButtonClickCountFrontend', 0, { path: '/' });
      })
      .catch(error => console.error(error));
    }
    fetchData();
    // eslint-disable-next-line
  }, [])
  
  return(
    <>
    <BrowserRouter> 
      <Routes>
        
        <Route path='/' element={<Home />}/>
        <Route path='/addNewUser' element={<AddInfo />}/>
        <Route path='/editUser' element={<EditInfo />}/>
      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App;
