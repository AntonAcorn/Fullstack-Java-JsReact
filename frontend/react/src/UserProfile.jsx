const UserProfile = ({ name, age, gender, ...props }) => {
    return (
        <div>
            <h1>{name}</h1>
            <p>{age}</p>
            <img
                src={`https://randomuser.me/api/portraits/${gender}/44.jpg`}
            />
            {props.children}
        </div>
    )
}

export default UserProfile;