import UserProfile from "./UserProfile.jsx";
function App() {
return (
    <div>
        <UserProfile
        name = {"Jamila"}
        age = {22}
        gender = {"women"}>
        </UserProfile>
        <UserProfile
            name = {"Anker"}
            age = {26}
            gender = {"men"}>
        </UserProfile>  <UserProfile
            name = {"Foster"}
            age = {12}
            gender = {"men"}>
        </UserProfile>
    </div>
    )
}

export default App
