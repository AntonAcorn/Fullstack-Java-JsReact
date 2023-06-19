const UserProfile = ({ name, age, gender }) => {
    return (
        <div>
            <h1>{name}</h1>
            <p>{age}</p>
            <img src={`https://randomuser.me/api/portraits/${gender}/66.jpg`}/>
        </div>
    )
}

export default UserProfile;