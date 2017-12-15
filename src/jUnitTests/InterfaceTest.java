package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import antiSpamFilter.Interface;
import antiSpamFilter.ReadRules;
import antiSpamFilter.Table_Model;
import antiSpamFilter.Table_object;

public class InterfaceTest {
	
	Interface i = new Interface();
			 
	@Test
	public final void test() throws IOException{
		Interface.main(null);
	
		assertEquals(("Escolha de paths"), i.paths.getTitle());
		assertEquals("Fechar janela", i.fechar.getText());
		
		JTextField spamField = new JTextField(10);
		JTextField rulesField = new JTextField(10);
		JTextField hamField = new JTextField(10);
		
		hamField.setText("ham.log");
		spamField.setText("spam.log");
		rulesField.setText("rules.cf");
		 
		String ham = hamField.getText();
		String spam = spamField.getText();		
		String rules = rulesField.getText();
		i.hamField.setText(ham);
		i.spamField.setText(spam);
		i.rulesField.setText(rules);
		i.fechar.doClick();
		assertNotSame(ReadRules.readRules("readrules"), ReadRules.readRules(rulesField.getText()));

		assertEquals(("Filtro Anti-SPAM"), i.frame.getTitle());
		Table_Model modelo_tabela_MAN = new Table_Model();
		JTextField FP_MAN = new JTextField();
		JTextField FN_MAN = new JTextField();
	
		i.calcFP(modelo_tabela_MAN, FP_MAN, ham);
		i.calcFN(modelo_tabela_MAN, FN_MAN, spam);
	
		assertNotSame(i.calcFN(modelo_tabela_MAN, FN_MAN, "fn.log"),i.calcFN(modelo_tabela_MAN, FN_MAN,spam ));
		assertSame(i.calcFP(modelo_tabela_MAN, FP_MAN, "fp.log"),i.calcFP(modelo_tabela_MAN, FP_MAN, ham ));
				
		assertEquals("Gravar configuração Automática", i.saveAUTO.getText());
		assertEquals("Gravar configuração Manual", i.saveMAN.getText());
		assertEquals("Avaliar configuração Manual", i.evaluateMAN.getText());
		assertEquals("Gerar configuração automática",i.evaluateAUTO.getText());
	
	
		i.evaluateMAN();
		i.evaluateAUTO();

		i.saveAUTO.doClick();
		i.saveMAN.doClick();
		i.evaluateAUTO.doClick();
		i.evaluateMAN.doClick();
		
	}

}
