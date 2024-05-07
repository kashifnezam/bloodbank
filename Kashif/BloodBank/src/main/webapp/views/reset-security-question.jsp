<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles.css">
        <title>Forgot Password</title>
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

select, input[type="text"] {
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
            <h3 style="color:red">${errorMsg}</h3>
            <h1>Forgot Password</h1>
            <form id="forgot-form" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
                <label for="security-question">Answer Your Security Question:</label>
                <select id="security-question" name="security-question" required>
                    <option value="">Select a security question</option>
                    <option value="q1">What is your favorite animal?</option>
                    <option value="q2">What is your favorite dish?</option>
                    <option value="q3">What is your favorite hobby?</option>
                </select>
                
                <input type="text" id="answer" name="answer" placeholder="Your Answer" required>
                
                <button type="submit">Submit</button>
            </form>
        </div>
    </body>
</html>