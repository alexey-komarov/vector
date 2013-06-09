package tao.nottao;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JSeparator;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToolBar;
import javax.swing.JCheckBox;
import javax.swing.JPopupMenu;

import tao.icons.Nuvola;

// @author subanark
public class PopupMenuLayout implements java.awt.LayoutManager 
{
  private JPopupMenu popupMenu = new JPopupMenu();
  private JButton popupButton = new JButton(new PopupAction());
 
  public PopupMenuLayout() 
  {
    popupButton.setMargin(new Insets(0, 0, 1, 1));
  }
 
  public void addLayoutComponent(String name, Component comp) {}
 
  public void layoutContainer(Container parent) 
  {
    Insets insets = parent.getInsets();
    int x = insets.left;
    int y = insets.top;
    int spaceUsed = insets.right + insets.left;

    for (int i = 0; i < parent.getComponentCount(); i++ )
    {
      Component aComponent = parent.getComponent(i);
      aComponent.setSize(aComponent.getPreferredSize());
      aComponent.setLocation(x,y);
      int componentWidth = aComponent.getPreferredSize().width;
      x += componentWidth;
      spaceUsed += componentWidth;
    }
 
    int parentWidth = parent.getSize().width;

    if (spaceUsed > parentWidth)
    {
      popupMenu.removeAll();
      parent.add(popupButton);
      popupButton.setSize( popupButton.getPreferredSize() );
      int popupX = parentWidth - insets.right - popupButton.getSize().width;
      popupButton.setLocation(popupX, y );
      spaceUsed += popupButton.getSize().width;
    }
 
    int lastVisibleButtonIndex = 1;
    try {
      while (spaceUsed > parentWidth)
      {
        lastVisibleButtonIndex++;
        int last = parent.getComponentCount() - lastVisibleButtonIndex;
        Component component = parent.getComponent( last );
        component.setVisible( false );
        spaceUsed -= component.getSize().width;
 
        addComponentToPopup(component);
      }
    } catch(Exception e){}
 
  }
 
  private void addComponentToPopup(Component component)
  {

    if (component instanceof JButton)
    {
      JButton button = (JButton)component;
      JMenuItem menuItem = new JMenuItem(button.getText());
      menuItem.setIcon( button.getIcon() );

      ActionListener[] listeners = button.getActionListeners();
 
      for (int i = 0; i < listeners.length; i++)
        menuItem.addActionListener( listeners[i] );

      popupMenu.insert(menuItem, 0);
    }

    if (component instanceof JCheckBox)
    {
      JCheckBox checkbox = (JCheckBox)component;
      JMenuItem menuItem = new JCheckBoxMenuItem(checkbox.getText());
      menuItem.setSelected(checkbox.isSelected());

      ActionListener[] listeners = checkbox.getActionListeners();
 
      for (int i = 0; i < listeners.length; i++)
        menuItem.addActionListener( listeners[i] );

      popupMenu.insert(menuItem, 0);
    }
 
    if (component instanceof JToolBar.Separator)
    {
       popupMenu.insert( new JSeparator(), 0);
    }
  }
 
  public Dimension minimumLayoutSize(Container parent)
  {
    return popupButton.getMinimumSize();
  }
 
  public Dimension preferredLayoutSize(Container parent)
  {
    parent.remove(popupButton);
    Dimension d = new Dimension();
    d.width += parent.getInsets().right + parent.getInsets().left;
 
    for (int i = 0; i < parent.getComponents().length; i++)
    {
      Component component = parent.getComponent(i);
      component.setVisible( true );
      d.width += component.getPreferredSize().width;
      d.height = Math.max(d.height, component.getPreferredSize().height);
    }
 
    // d.height += parent.getInsets().top + parent.getInsets().bottom + 5;
    d.height += parent.getInsets().top + parent.getInsets().bottom ;
    return d;
  }

  public void removeLayoutComponent(Component comp) { }
 
  protected class PopupAction extends AbstractAction
  {
    public PopupAction()
    {
      // super(">>");

      putValue(Action.NAME, "");
      putValue(Action.SHORT_DESCRIPTION, "Åùå");
      putValue(Action.SMALL_ICON, Nuvola.actions.s_2rightarrow);
      // putValue(Action.LARGE_ICON_KEY, Nuvola.actions.l_1downarrow);

    }

    public void actionPerformed(ActionEvent e)
    {
      JComponent component = (JComponent)e.getSource();
      popupMenu.show(component,0,component.getHeight());
    }
  }
 
}