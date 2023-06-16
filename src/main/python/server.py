from flask import Flask, render_template
from solution import cardfromgorilla

app = Flask(__name__)
 
@app.route('/tospring')
def main():
    return cardfromgorilla.scraping()
 
if __name__ == '__main__':
    app.run()