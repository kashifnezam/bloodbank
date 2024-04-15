<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Security Question Registration</title>
    </head>
    <style>
        * {
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f9f9f9;
    margin: 0;
    padding: 0;
}

.container {
    width: 100%;
    max-width: 400px;
    margin: 50px auto;
    background-color: #fff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h1 {
    color: #333;
    font-size: 1.5rem;
    margin-bottom: 20px;
}

label {
    display: block;
    margin-bottom: 10px;
    font-size: 1rem;
    color: #555;
}

input[type="text"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 1rem;
    color: #333;
    margin-bottom: 20px;
}

button[type="submit"] {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button[type="submit"]:hover {
    background-color: #0056b3;
}
    </style>
    <body>
        <div class="container">
            <h1>Set Your Security Questions</h1>
            <form action="#" id="security-form" method="post">
                <label for="question-1">Favorite Animal:</label>
                <input type="text" id="question-1" name="question-1" required>
                
                <label for="question-2">Favorite Dish:</label>
                <input type="text" id="question-2" name="question-2" required>
                
                <label for="question-3">Favorite Hobby:</label>
                <input type="text" id="question-3" name="question-3" required>
                
                <button type="submit">Register</button>
            </form>
        </div>
    </body>
</html>