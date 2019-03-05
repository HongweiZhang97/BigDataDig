package service;

import tool.neuralnetwork.Anticipate;

public class AnticipateService {
	public int anticipate(double[] params) {
		Anticipate anti = new Anticipate(params);
		anti.anticipate();
		return anti.getAntiResult();
	}
}
