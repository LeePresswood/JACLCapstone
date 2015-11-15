package com.jacl.capstone.world.entities.player.items;

public class Item {
		//set of all atributes
		private String name;
		private int weight;
		private String level;
		/**
		 * constructor
		 * @param name
		 * @param weight
		 * @param level
		 */
		public Item(String name, int weight,String level) {
			setName(name);
			setWeight(weight);
			setLevel(level);
		}
		/**
		 * mutator for level
		 * @param level
		 */
		private void setLevel(String level) {
			this.level=level;
		}
		/**
		 * mutator for name
		 * @param name
		 */
		private void setName(String name) {
			this.name = name;
		}
		/**
		 * mutator for weight
		 * @param w
		 */
		private void setWeight(int w) {
			if (w >= 0) {
				this.weight = w;
			}
			else {
				this.weight = 0;
			}
		}
		/**
		 * accessor for name
		 * @return
		 */
		public String getName() {
			return this.name;
		}
		/**
		 * accessor for weight
		 * @return
		 */
		public int getWeight() {
			return this.weight;
		}

		/**
		 * accessor for level
		 * @return
		 */
		public String getLevel() {
			return this.level;
		}
		
		public int getPoints(){
			return 0;
		}
}
