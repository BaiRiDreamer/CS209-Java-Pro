import React, { useEffect, useState } from 'react';
import { fetchUsers } from '../api';

const Users = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers().then(response => setUsers(response.data));
  }, []);

  return (
    <div>
      <h2>Users</h2>
      <ul>
        {users.map(user => (
          <li key={user.user_id} style={{ marginBottom: '20px' }}>
            <img
              src={user.profile_image}
              alt={user.display_name}
              width="50"
              height="50"
              style={{ borderRadius: '50%', marginRight: '10px' }}
            />
            <strong>{user.display_name}</strong> (Reputation: {user.reputation}) - 
            <a href={user.link} target="_blank" rel="noopener noreferrer">Profile</a>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Users;
