from flask import Flask, render_template
from solution import cardfromgorilla

app = Flask(__name__)
 
@app.route('/')
def main():
    cardinfo = cardfromgorilla.scraping()
    print("뽑았어용")
    print(cardinfo)
    return render_template('index.html')
 
if __name__ == '__main__':
    app.run()