<%
 // This is just if you want to kill the session for the current user so you can start fresh without restarting tomcat.
    // Good for when making changes to the component tree
    session.invalidate();
%>
Session Dead
<br/>
<br/>
<a href="index.jsf">Home</a>
