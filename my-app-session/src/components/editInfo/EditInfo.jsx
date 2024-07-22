import { useEffect, useState } from "react"
import { useCookies } from "react-cookie";
import "./EditInfo.css"
import { Link } from "react-router-dom";

export function EditInfo(){

    //dispatch is use if we have to send some data.
    const [editUser, setEditUser] = useState(JSON.parse(sessionStorage.getItem("editUser")));
    const [cookies, setCookie] = useCookies(['editUserButtonClickCountFrontend']);
    useEffect(()=>{
        sessionStorage.setItem('editUser', JSON.stringify(editUser));
    },[editUser])

    const updateValue = (value) => {
        const updatedValue = {...editUser, ...value}
        setEditUser(updatedValue);
    }

    const onSubmit = (event) => {
        event.preventDefault();
        if(editUser.name==="" || editUser.address === "" || editUser.mobileNo === "" || editUser.position ===""){
            alert("Data is incompleted");
        }
        else{
            fetch(`http://localhost:8080/practiceCrud/editUserInfo/${editUser.userId}`, {
                method: 'PUT',
                body: JSON.stringify(editUser),
                headers: {
                  'Content-type': 'application/json; charset=UTF-8',
                },
                credentials: 'include'
              })
                 .then(response => response.json())
                 .then((json) => {
                    if(json.status === "SUCCESS"){
                        alert("Data Edited successfully");
                        const users = JSON.parse(sessionStorage.getItem('users'));
                        const editedUsers = users.map((user)=>{
                            if(user.userId === editUser.userId){
                                return editUser;
                            }
                            else return user;
                        })
                        sessionStorage.setItem('users', JSON.stringify(editedUsers));
                    }
                    else{
                        alert("Failed to save the data");
                    }
                 })
                 .catch((err) => {
                    console.log("Error message = " + err.message);
                    alert("Failed to save the data");
                    return;
                 });            
        }
        const count = cookies.editUserButtonClickCountFrontend;
        setCookie('editUserButtonClickCountFrontend', count+1, { path: '/' });
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
                        <td><input type="text" value={editUser.name} onChange={(e)=>{updateValue({ "name": e.target.value})}}/></td>
                    </tr>
                    <tr>
                        <th>Address:</th>
                        <td><input type="text" value={editUser.address} onChange={(e)=>{updateValue({ "address": e.target.value})}}/></td>
                    </tr>
                    <tr>
                        <th>Mobile No:</th>
                        <td><input type="text" value={editUser.mobileNo} onChange={(e)=>{updateValue({ "mobileNo": e.target.value})}}/></td>
                    </tr>
                    <tr>
                        <th>Position:</th>
                        <td><input type="text" value={editUser.position} onChange={(e)=>{updateValue({ "position": e.target.value})}}/></td>
                    </tr>
                    </tbody>
                </table>
                <button type="submit" >Submit</button>
            </form>
            </div>
        </>
    )
}