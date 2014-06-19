
public class Combat {
	
	//HP
	private float maxHP, currentHP, enemyMaxHP, enemyCurrentHP;
	//Attack
	private int attack;
	private String playerAttack, enemyAttack;
	
		public Combat(){
		}
			
			public void setFight(Genetics player, Genetics enemy){
				maxHP = player.returnConstitution() *4;
				currentHP = player.returnConstitution() *4;
				enemyMaxHP = enemy.returnConstitution() *4;
				enemyCurrentHP = enemy.returnConstitution() *4;
				
				if(enemy.returnDexterity() > player.returnDexterity()){
					//Enemy gets first strike
					if(dodgeCalc(enemy, player)){
						playerDamage(enemy, player);
						enemyAttack = "Your monster took "+attackCalc(enemy, player)+" damage!";
					}else{
						enemyAttack = "Your enemys attack missed!";
					}
					
				}
			}
			
			public String getPlayerAttack(){
				return playerAttack;
			}
			
			public String getEnemyAttack(){
				return enemyAttack;
			}
			
			public float getCurHP(){
				return currentHP;
			}

			public float getMaxHP(){
				return maxHP;
			}
			
			public float getEnemyCurHP(){
				return enemyCurrentHP;
			}

			public float getEnemyMaxHP(){
				return enemyMaxHP;
			}			
			
			//This comprises various other methods within the class to make one player turn
			public void fightTurn(Genetics player, Genetics enemy){
				//Players turn
					if(dodgeCalc(player, enemy)){
						enemyDamage(player, enemy);
						playerAttack = "The opponent took "+attackCalc(player, enemy)+" damage!";
					}else{
						playerAttack = "Player attack missed!";
					}

				//Enemies turn
				if(dodgeCalc(enemy, player)){
					playerDamage(enemy, player);
					enemyAttack = "Your monster took "+attackCalc(enemy, player)+" damage!";
				}else{
					enemyAttack = "Your enemys attack missed!";
				}
			}
			
			public int attackCalc(Genetics attacker, Genetics defender){
				//I've done this calculation in steps so it's easier to follow
				//First base damage is calculated
				
				attack = (int)(attacker.returnStrength() *0.25) + ((int) (Math.random() * attacker.returnStrength() *0.5));
				//Then opponents damage resistance
				attack = (int)attack;
				return attack;
			}
			
			//Used when player monster is damaged.
			public void playerDamage(Genetics attacker, Genetics defender){
				currentHP = currentHP - attackCalc(attacker, defender);
			}
			
			//Used when enemy monster is damaged.
			public void enemyDamage(Genetics attacker, Genetics defender){
				enemyCurrentHP = enemyCurrentHP - attackCalc(attacker, defender);
			}
			
			public boolean dodgeCalc(Genetics attacker, Genetics defender){
				float dexComparison = attacker.returnConstitution() / defender.returnConstitution();
				if(dexComparison+(Math.random()*20)>1){
					return true;
				}else{
					return false;
				}
			}
	
			public int winLossCheck(){
				if(currentHP<=0){
					return 1;	//Lose
			}else if(enemyCurrentHP<=0){
				return 2; 		//Win
			}else{
				return 0;		//Still fighting
			}
			}
}