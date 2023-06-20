import UserProfile from "./UserProfile.jsx";
function App() {
return (
    <div>
        <UserProfile
        name = {"Jamila"}
        age = {21}
        gender = {"women"}>
        <p>Hello</p>
        </UserProfile>
        <UserProfile
            name = {"Marco"}
            age = {24}
            gender = {"men"}>
            <h1>Ciao!</h1>
        </UserProfile>
    </div>
    )
}

export default App
