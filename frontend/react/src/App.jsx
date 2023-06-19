import UserProfile from "./UserProfile.jsx";
function App() {
return (
    <div>
        <UserProfile
        name = {"Jamila"}
        age = {21}
        gender = {"women"}
        />
        <UserProfile
            name = {"Marco"}
            age = {24}
            gender = {"men"}
        />
    </div>
    )
}

export default App
