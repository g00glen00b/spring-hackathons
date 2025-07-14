package codes.dimitri.apps.markdownnotes;

import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.ext.gfm.tables.TableRow;
import org.commonmark.node.Code;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.Text;
import org.commonmark.renderer.Renderer;

public class PlainTextRenderer implements Renderer {
    @Override
    public void render(Node node, Appendable appendable) {
        this.render(node);
    }

    @Override
    public String render(Node node) {
        StringBuilder sb = new StringBuilder();
        collectText(node, sb);
        return sb.toString();
    }

    private void collectText(Node node, StringBuilder sb) {
        if (node instanceof TableRow) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNext()) {
                collectText(child, sb);
                if (child.getNext() != null) sb.append("\t");
            }
            sb.append("\n");
            return;
        } else if (node instanceof TableCell) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNext()) {
                collectText(child, sb);
            }
            return;
        } else if (node instanceof Text text) {
            sb.append(text.getLiteral());
        } else if (node instanceof Code code) {
            sb.append(code.getLiteral());
        } else if (node instanceof SoftLineBreak || node instanceof HardLineBreak) {
            sb.append("\n");
        }
        for (Node child = node.getFirstChild(); child != null; child = child.getNext()) {
            collectText(child, sb);
        }
        if (node instanceof Heading || node instanceof ListItem || node instanceof Paragraph) {
            sb.append("\n");
        }
    }
}