import { Link } from "react-router-dom";
import './Home.css'
import { useState } from "react";

export function Home(){

    // const userInfo = JSON.parse(sessionStorage.getItem('users'));
    const [userInfo, setUserInfo] = useState(JSON.parse(sessionStorage.getItem('users')));

    const deleteData =async (user) =>{
        await fetch(`http://localhost:8080/practiceCrud/user/${user.userId}`, {
            method: 'DELETE',
          })
             .then((response) => response.json())
             .then((data) => {
                console.log(data);
                if(data === "SUCCESS"){
                    const persons = userInfo.filter((person)=> person.userId !== user.userId);
                    setUserInfo(persons);
                    sessionStorage.setItem('users', JSON.stringify(persons));
                    alert("Data deleted successfully of "+ user.name);
                }
                else{
                    alert("Data can not be deleted." + user.name + " not found.");
                }
             })
             .then(() => {
                console.log(JSON.parse(sessionStorage.getItem('users')));
             })
             .catch((err) => {
                console.log(err.message);
                alert("Error ");
             });
        
    }

    const editClickButton = (user) => {
        sessionStorage.setItem('editUser', JSON.stringify(user));
    }

    return(
        <>
        <table>
            <thead>
            <tr>
                <th>S.No.</th>
                <th>Name.</th>
                <th>Address</th>
                <th>Mobile No.</th>
                <th>Position</th>
                <th>Delete User</th>
                <th>Edit User</th>
            </tr>
            </thead>
            <tbody>
            {userInfo!==null ?<>
            {userInfo.map((user, index)=>(
                <tr key={user.userId}>
                    <td>{index+1}</td>
                    <td>{user.name}</td>
                    <td>{user.address}</td>
                    <td>{user.mobileNo}</td>
                    <td>{user.position}</td>
                    <td><Link onClick={()=>{deleteData(user)}}>D</Link></td>
                    <td key={index}><Link to={"/editUser"} onClick={()=>{editClickButton(user)}}>E</Link></td>
                </tr>
            ))}</>:<></>}

            <tr>
                <td colSpan={7}><Link to={"../addNewUser"} className="button">Add New Info</Link></td>
            </tr>
            </tbody>
        </table>
        </>
    )
}