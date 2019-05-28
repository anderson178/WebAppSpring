$(document).ready(function () {

    // $.get( "http://127.0.0.1:8080/map/getById?parm={parm}", { name: "John", time: "2pm" } );

    // $("#button").click(function () {
    //     $.get("http://127.0.0.1:8080/getById", {parm: 1})
    //         .done(function (data) {
    //             // alert( "Data Loaded: " + data );
    //             // var obj = jQuery.parseJSON(data);
    //             // alert(obj['last_name']);
    //             alert(data['lastName'])
    //         })
    //         .fail(function () {
    //             alert("error");
    //         });
    // });
    var outUrl = "http://127.0.0.1:8080";
    $("form").on("submit", function (event) {
        var button = $(document.activeElement).attr('id');
        var formData = new FormData(document.getElementsByTagName('form')[0]);
        switch (button) {
            case "add":
                console.log(formData);
                postQuery(outUrl + "/addPerson", formData);
                break;
            case "get":
                getFindById(outUrl + "/findById", formData);
                break;
            case "remove":
                postQueryRemove(outUrl + "/removePerson", formData);
                break;
            case "update":
                postQuery(outUrl + "/updatePerson", formData);
                break;
        }
        event.preventDefault();
        console.log($(this).serialize());
    });

    function getFindById(url, formData) {
        var id = formData.get('id');
        $.ajax({
            url: url,
            type: 'POST',
            data: JSON.stringify(id),
            contentType: 'application/json; charset=utf-8',
            async: true,
            success: function (data) {
                if (data['id'] == '-1') {
                    alert('Not exist is person with id ' + id);
                } else {
                    fillForm(data);
                }
            },
            error: function (data) {
                alert(data);
            }
        });

    }

    function fillForm(data) {
        console.log($(this).serialize());
        document.getElementById('firstName').value = data['firstName'];
        document.getElementById('lastName').value = data['lastName'];
        document.getElementById('middleName').value = data['middleName'];
        var formated_date = new Date(data['birthDate']).toLocaleDateString().split('.');
        var mm = formated_date[1];
        var dd = formated_date[0];
        var yyyy = formated_date[2];
        document.getElementById('birthDate').value = yyyy + "-" + mm + "-" + dd;
    }

    function postQueryRemove(url, formData) {
        var id = formData.get('id');
        alert(id);
        $.ajax({
            url: url,
            type: 'POST',
            data: JSON.stringify(id),
            contentType: 'application/json; charset=utf-8',
            async: true,
            success: function (data) {
                if (data['id'] == '-1') {
                    alert('Not exist is person with id ' + id);
                }
            },
            error: function (data) {
                alert(data);
            }
        });
    }

    function postQuery(url, formData) {
        var person = {
            id: formData.get('id'),
            firstName: formData.get('firstName'),
            lastName: formData.get('lastName'),
            middleName: formData.get('middleName'),
            birthDate: formData.get('birthDate')
        };
        alert(JSON.stringify(person));
        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(person),
            contentType: "application/json; charset=utf-8",
            processData: false,
            success: function (data) {
                alert(data);
            },
            error: function (data) {
                alert(data);
            }
        });
    }

    $("#fillButton").click(function () {
        $.get(outUrl + "/getAll")
            .done(function (data) {
                $('#names tbody').empty();
                for (var i = 0; i < data.length; i++) {
                    var now = new Date(data[i]['birthDate']);
                    var formated_date = now.toLocaleDateString();
                    $('#names tbody').append('<tr><td><input type="checkbox" id="blahA" value="1"/></td>   <td>' + data[i]['id'] + '</td><td>' + data[i]['firstName'] + '</td><td>' + data[i]['lastName'] + '</td><td>' + data[i]['middleName'] + '</td> <td>' + formated_date + '</td>    <td>' + data[i]['comment'] + '</td></tr>')
                    //<td>' + date.getFullYear() + ' ' + date.getMonth() + ' ' + date.getDate() + '</td>
                }
            })
    });

    $("#processButton").click(function () {
        var checked = [];
        $("#names input[type=checkbox]:checked").each(function () {
            var row = $(this).closest("tr")[0];
            checked.push(row.cells[1].innerHTML);
        });
        $.ajax({
            url: outUrl + '/updatePersons',
            type: 'POST',
            data: JSON.stringify(checked),
            contentType: 'application/json; charset=utf-8',
            async: true,
            success: function (msg) {
                alert(msg);
            },
            error: function (data) {
                alert(data);
            }
        });

    });


// $("#processButton").click(function () {    //
//     var checked = [];
//     $("#names input[type=checkbox]:checked").each(function () {
//         var row = $(this).closest("tr")[0];
//         var person = {
//             id: row.cells[1].innerHTML,
//             firstName: row.cells[2].innerHTML,
//             lastName: row.cells[3].innerHTML,
//             middleName: row.cells[4].innerHTML,
//             birthDate:row.cells[5].innerHTML
//         };
//         checked.push(person);
//         //checked.push("   "+row.cells[1].innerHTML + "   " + row.cells[2].innerHTML + "   " + row.cells[3].innerHTML + "   " + row.cells[4].innerHTML + "   " + row.cells[5].innerHTML+"\n")
//     });
//     $.post("http://127.0.0.1:8080/updatePersons", checked);
//     alert(checked);
// });


})
;