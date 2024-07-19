import { useEffect, useState } from "react"
import "./AddInfo.css"
import { Link } from "react-router-dom";

export function AddInfo(){

    //dispatch is use if we have to send some data.
    const [newUser, setNewUser] = useState(JSON.parse(sessionStorage.getItem("newUser")));

    useEffect(()=>{
        sessionStorage.setItem('newUser', JSON.stringify(newUser));
    },[newUser])

    const updateValue = (value) => {

        const updatedValue = {...newUser, ...value}
        setNewUser(updatedValue);
        // sessionStorage.setItem('newUser', JSON.stringify(updatedValue))
    }

    const onSubmit = (event) => {
        event.preventDefault();
        if(newUser.name==="" || newUser.address === "" || newUser.mobileNo === "" || newUser.position ===""){
            alert("Data is incompleted");
        }
        else{
            fetch('http://localhost:8080/practiceCrud/addUserInfo', {
                method: 'POST',
                body: JSON.stringify(newUser),
                headers: {
                  'Content-type': 'application/json; charset=UTF-8',
                },
              })
                 .then(response => response.json())
                 .then((json) => {
                    if(json){
                        alert("Data saved successfully");
                        setNewUser({"name":"", "address":"", "mobileNo":"", "position":""})
                        sessionStorage.setItem('newUser', JSON.stringify({"name":"", "address":"", "mobileNo":"", "position":""}))
                        const users = JSON.parse(sessionStorage.getItem('users'));
                        users.push(json);
                        sessionStorage.setItem('users', JSON.stringify(users));
                    }
                    else{
                        alert("Failed to save the data");
                    }
                 })
                 .catch((err) => {
                    console.log(err.message);
                    alert("Failed to save the data");
                    return;
                 });            
        }
    }
    return(
        <>
            <div className="addInfo">
            <Link className="linkButton" to={"../"}><div className="backButton">{"<<"}Back</div></Link>
            <h1>Add the user Information</h1>
            <form onSubmit={onSubmit}>
                <table>
                    <tbody>
                    <tr>
                        <th>Name:</th>
                        <td><input type="text" value={newUser.name} onChange={(e)=>{updateValue({ "name": e.target.value})}}/></td>
                    </tr>
                    <tr>
                        <th>Address:</th>
                        <td><input type="text" value={newUser.address} onChange={(e)=>{updateValue({ "address": e.target.value})}}/></td>
                    </tr>
                    <tr>
                        <th>Mobile No:</th>
                        <td><input type="text" value={newUser.mobileNo} onChange={(e)=>{updateValue({ "mobileNo": e.target.value})}}/></td>
                    </tr>
                    <tr>
                        <th>Position:</th>
                        <td><input type="text" value={newUser.position} onChange={(e)=>{updateValue({ "position": e.target.value})}}/></td>
                    </tr>
                    </tbody>
                </table>
                <button type="submit" >Submit</button>
            </form>
            </div>
        </>
    )
}