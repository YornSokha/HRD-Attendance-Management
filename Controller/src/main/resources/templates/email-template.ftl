<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Java Techie Mail</title>
    <style type="text/css">
        .form-style-3 {
            max-width: 450px;
            font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif;
            margin: 0 auto;
        }

        .form-style-3 label {
            display: block;
            margin-bottom: 10px;
        }

        .form-style-3 label > span {
            float: left;
            width: 100px;
            color: #3c3d40;
            font-weight: 600;
            font-size: 13px;
            text-shadow: 1px 1px 1px #fff;
        }

        .form-style-3 fieldset {
            margin: 0px 0px 10px 0px;
            border: 1px solid #f2711c;
            padding: 20px;
            border-radius: 5px;
        }

        .form-style-3 fieldset legend {
            color: #f2711c;
            border-top: 1px solid #f2711c;
            border-left: 1px solid #f2711c;
            border-right: 1px solid #f2711c;
            border-bottom: 1px solid #f2711c;
            border-radius: 5px;
            padding: 2px 34px 4px 34px;
            font-weight: bold;
            font-size: 17px;
        }

        .form-style-3 textarea {
            width: 250px;
            height: 100px;
            border-radius: 5px;
            border: 1px solid #f2711c;
        }

        .form-style-3 a {
            font-family: 'Google Sans', Roboto, RobotoDraft, Helvetica, Arial, sans-serif;
            line-height: 16px;
            color: #ffffff;
            font-weight: 400;
            text-decoration: none;
            font-size: 14px;
            display: inline-block;
            padding: 10px 24px;
            background-color: #a333c8;
            border-radius: 5px;
            float: right;
        }

        input.input-field.custom {
            margin-left: 8px;
            border: 1px solid #f2711c;
            padding: 5px 16px;
            border-radius: 5px;
        }
    </style>
</head>

<body>


<form>
    <div class="form-style-3">
        <fieldset>
            <legend>Personal</legend>
            <label for="field1">
                <span>Student's name</span>
                <input type="text" class="input-field custom" name="field1" value="${name}"/>
            </label>
            <label for="field2">
                <span>Subject</span>
                <input type="email" class="input-field custom" name="field2" value="${subject}"/>
            </label>
            <label for="field3">
                <span>Date</span>
                <input type="text" class="input-field custom" name="field3" value="${requestedDate}"/>
            </label>
            <label for="field4">
                <span>leave Time</span>
                <input type="text" class="input-field custom" name="field4" value="${leaveTime}"/>
            </label>
            <label for="field5">
                <span>Arrive Time</span>
                <input type="text" class="input-field custom" name="field5" value="${arriveTime}"/>
            </label>
        </fieldset>
        <fieldset>
            <legend>Message</legend>
            <label for="field6">
                <span>Reason</span><textarea name="field6" class="textarea-field">${reason}</textarea></label>
        </fieldset>
        <label><a href="${url}">Approve Request</a></label>
    </div>
</form>
</body>
</html>
