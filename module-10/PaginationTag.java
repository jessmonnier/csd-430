/* CSD-430 Module 10 Example Code
 * Jess Monnier
 * 5 October 2025
 * Note these examples are not arranged within the appropriate directory structure
 * I kept the directory structure flat just to keep things easy to look at
 */

package myTags;

import java.io.IOException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;

public class PaginationTag extends BodyTagSupport {
    
    // variables to mark location w/i the navigation
    private int currentPage;
    private int totalPages;
    private String baseUrl;

    // setters
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // We can skip the body content bc it should not impact the navigation in this case
    @Override
    public int doStartTag() throws JspException {
        // No need to evaluate body content for pagination tag
        return SKIP_BODY;
    }

    // handle the actual pagination
    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();

            if (totalPages <= 1) {
                return EVAL_PAGE; // No pagination needed
            }

            out.write("<nav class='pagination'>");

            // Previous button
            if (currentPage > 1) {
                out.write("<a href='" + baseUrl + (currentPage - 1) + "'>Prev</a> ");
            } else {
                out.write("<span class='disabled'>Prev</span> ");
            }

            // Page numbers
            for (int i = 1; i <= totalPages; i++) {
                if (i == currentPage) {
                    out.write("<span class='current'>" + i + "</span> ");
                } else {
                    out.write("<a href='" + baseUrl + i + "'>" + i + "</a> ");
                }
            }

            // Next button
            if (currentPage < totalPages) {
                out.write("<a href='" + baseUrl + (currentPage + 1) + "'>Next</a>");
            } else {
                out.write("<span class='disabled'>Next</span>");
            }

            out.write("</nav>");
        } catch (IOException e) {
            throw new JspException("Error in PaginationTag", e);
        }

        return EVAL_PAGE;
    }
}