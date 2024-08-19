function verify() {
    var password1 = document.forms['form']['password'].value;
    var password2 = document.forms['form']['verifyPassword'].value;

    if (password1 == null || password1 == "") {
        document.getElementById("error").innerHTML = "Password cannot be empty.";
        return false;
    }

    if (password1.length < 5) {
        document.getElementById("error").innerHTML = "Password must be at least 5 characters long.";
        return false;
    }

    if (password1 != password2) {
        document.getElementById("error").innerHTML = "Your passwords do not match.";
        return false;
    }

    return true;
}
