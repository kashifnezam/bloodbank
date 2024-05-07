<jsp:include page="header.jsp" />

<div class="main">
    <aside class="sidebar">
        <ul>
            <li><a href="/dashboard">Dashboard</a></li>
            <li><a href="/dashboard/agent-account-creation" class="active">Register Agent</a></li>
            <li><a href="/dashboard/alluser-lists">User Lists</a></li>
            <li><a href="/dashboard/blood-request">Blood Requests</a></li>
            <li><a href="/dashboard/coin-request-list">Coin Requests</a></li>
            <li><a href="/dashboard/blood-report">Blood Report</a></li>
            <li><a href="/dashboard/available-stock">Available Stock</a></li>
            <li><a href="/dashboard/coins-report">Coins Report</a></li>
        </ul>
    </aside>
    
    <main class="content">
        <h2 style="text-align:center; margin-bottom:10px">Agent Account Creation </h2>
        <h3 style="color:red; text-align:center"> ${errorMsg} </h3>
        <div class="agent-form">
            <form action="/dashboard/agent-account-creation" method="POST">
                <div class="form-row">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" required>
                </div>
                
                <div class="form-row">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <div class="form-row">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                
                <div class="form-row">
                    <label for="dob">Date of Birth</label>
                    <input type="date" id="dob" name="dob" required>
                </div>
                
                <div class="form-row">
                    <label for="commission">Commission in %</label>
                    <input type="number" id="commission" name="commission" required>
                </div>
                
                <div class="form-row">
                    <label for="address">Address</label>
                    <textarea id="address" name="address" rows="4" required></textarea>
                </div>
                
                <!-- Profile Picture URL input field -->
                <div class="form-row">
                    <label for="profilePic">Profile Picture URL</label>
                    <input type="url" id="profilePic" name="profilePicURL"
                           title="Save your profile picture on a cloud storage service like Google Drive, Dropbox, etc., and then paste the link here."
                           placeholder="Paste URL here">
                </div>
                
                <input id="create-account-btn" type="submit" value="Create Account">
            </form>
            <h2 style="color:green"> ${success} </h2>
        </div>
    </main>
</div>
<jsp:include page="footer.jsp" />
