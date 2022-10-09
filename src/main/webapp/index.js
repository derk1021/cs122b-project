function handleStarResult(resultData) {
    console.log("handleStarResult: populating star table from resultData");

    // Populate the star table
    // Find the empty table body by id "star_table_body"
    let starTableBodyElement = jQuery("#movies_table_body");

    for (let i = 0; i < resultData.length; i++) {


        // Concatenate the html tags with resultData jsonObject
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML +=
            "<th>" +
            // Add a link to single-star.html with id passed with GET url parameter
                 '<a href="single-movie.html?id=' + resultData[i]['movie_id'] + '">'
                + resultData[i]["movie_title"] +     // display star_name for the link text
                '</a>' + "</th>"

        rowHTML += "<th>" + resultData[i]["movie_year"] + "</th>";
        rowHTML += "<th>" + resultData[i]["movie_dir"] + "</th>";
        rowHTML += "<th>" + resultData[i]["movie_rating"] + "</th>";

        const genres_check = new Set();
        const genres = resultData[i]["genres_name"].split(",");
        let limit_check = 0;
        let genres_string = "";
        while (limit_check < genres.length || genres_check.length < 3) {
            if (!genres_check.has(genres[limit_check])) {
                genres_check.add(genres[limit_check]);
                genres_string += genres[limit_check] + "\n | ";

            }
            limit_check += 1;
        }
        rowHTML += "<th>" + genres_string.slice(0, -3) + "</th>";

        const stars = resultData[i]['star_name'].split(",");
        const starId = resultData[i]['star_id'].split(",");
        let stars_string = "";
        for (let j = 0; j < 3; j++) {
            stars_string += '<a href=\"single-star.html?id=' + starId[j] +'">'
                            + stars[j] + '</a>\n | ';
        }
        rowHTML += "<th>" + stars_string.slice(0, -3) + "</th>";
        rowHTML += "</tr>";

        // Append the row created to the table body, which will refresh the page
        starTableBodyElement.append(rowHTML);
    }
}


/**
 * Once this .js is loaded, following scripts will be executed by the browser
 */

// Makes the HTTP GET request and registers on success callback function handleStarResult
jQuery.ajax({
    dataType: "json", // Setting return data type
    method: "GET", // Setting request method
    url: "api/movies", // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleStarResult(resultData) // Setting callback function to handle data returned successfully by the StarsServlet
});