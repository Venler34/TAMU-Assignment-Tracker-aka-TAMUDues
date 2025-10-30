# TAMUDues
Collects due dates from Canvas so TAMU students know what to prioritize. 


Refactor so uses service layer for assignments and users
- Don't want to send multiple http requests between layers bad design so JWT tokens can extract assignments

- User Service (logic with users) (finding users / creating users / updating users) (no http requests)
- userServiceAPI - findAll(), registerUser(), getUserById()

- Assignment Service (logic with assignmnets)

- then route through assignment and user service - cause otherwise would have to send http requests between
- layers to actually get information. More modular and better.

- Service repo needs to be designed <= 
- Afterword should implement JWT service more modular and utilize user service as well
- Once we implement this we should be able to log in (the rest should then be frontend design which can be vibecoded)


Future Considerations
- Logic for enforcing unique usernames