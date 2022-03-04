$(document).ready(function () {
    alert("Hey test!!!");
    console.log("test log");
    productsAdd();
});

    // Add products to <table>
    function productsAdd() {
    // First check if a <tbody> tag exists, add one if not
    if ($("#productTable tbody").length == 0) {
      $("#productTable").append("<tbody></tbody>");
    }

    // Append product to the table
    $("#productTable tbody").append(
      "<tr>" +
        "<td>Extending Bootstrap with CSS,
             JavaScript and jQuery</td>" +
        "<td>6/11/2015</td>" +
        "<td>http://bit.ly/1SNzc0i</td>" +
      "</tr>"
      );

    $("#productTable tbody").append(
      "<tr>" +
        "<td>Build your own Bootstrap Business
             Application Template in MVC</td>" +
        "<td>1/29/2015</td>" +
        "<td>http://bit.ly/1I8ZqZg</td>" +
      "</tr>"
      );
    }

  function productAddToTable() {
  // First check if a <tbody> tag exists, add one if not
  if ($("#productTable tbody").length == 0) {
    $("#productTable").append("<tbody></tbody>");
  }

  // Append product to the table
  $("#productTable tbody").append(
      "<tr>" +
        "<td>" + $("#productname").val() + "</td>" +
        "<td>" + $("#introdate").val() + "</td>" +
        "<td>" + $("#url").val() + "</td>" +
      "</tr>"
      );
  }

function productAddToTable() {
  // First check if a <tbody> tag exists, add one if not
  if ($("#productTable tbody").length == 0) {
    $("#productTable").append("<tbody></tbody>");
  }

  // Append product to the table
  $("#productTable tbody").append(
    "<tr>" +
      "<td>" + $("#productname").val() + "</td>" +
      "<td>" + $("#introdate").val() + "</td>" +
      "<td>" + $("#url").val() + "</td>" +
      "<td>" +
        "<button type='button' " +
                "onclick='productDelete(this);' " +
                "class='btn btn-default'>" +
                "<span class='glyphicon
                              glyphicon-remove' />" +
        "</button>" +
      "</td>" +
    "</tr>"
  );
}

function productBuildTableRow(id) {
  var ret =
  "<tr>" +
    "<td>" +
      "<button type='button' " +
              "onclick='productDisplay(this);' " +
              "class='btn btn-default' " +
              "data-id='" + id + "'>" +
              "<span class='glyphicon glyphicon-edit' />" +
      "</button>" +
    "</td>" +
    "<td>" + $("#productname").val() + "</td>" +
    "<td>" + $("#introdate").val() + "</td>" +
    "<td>" + $("#url").val() + "</td>" +
    "<td>" +
      "<button type='button' " +
              "onclick='productDelete(this);' " +
              "class='btn btn-default' " +
              "data-id='" + id + "'>" +
              "<span class='glyphicon glyphicon-remove' />" +
      "</button>" +
    "</td>" +
  "</tr>"

  return ret;
}