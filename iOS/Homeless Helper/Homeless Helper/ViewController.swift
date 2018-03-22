//
//  ViewController.swift
//  Homeless Helper
//
//  Created by Armand Raynor on 2/14/18.
//  Copyright © 2018 Armand Raynor. All rights reserved.
//

import UIKit
import Material
import Firebase
import FirebaseDatabase

struct GlobalVariables {
    static var users : [String: String] = [String : String]()
    static var shelterDict = [[String]]()
}

class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var emailField: TextField!
    @IBOutlet weak var passwordField: TextField!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func loginClicked(_ sender: Any) {

        let email = emailField.text
        let password = passwordField.text

        Auth.auth().signIn(withEmail: email!, password: password!) { (user, error) in
            if error == nil {

                if (user?.isEmailVerified)! {
                    self.performSegue(withIdentifier: "toHome", sender: nil)
                } else {
                    let alert = UIAlertController(title: "Whoops!", message: "Please verify your email to login.",
                                                  preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "Okay", style: UIAlertActionStyle.default, handler: nil))

                    alert.addAction(UIAlertAction(title: "Resend Email", style: .default, handler: { (_) in
                        user?.sendEmailVerification(completion: nil)
                    }))
                    self.present(alert, animated: true, completion: nil)
                }
            } else {

                let alert = UIAlertController(title: "Whoops!",
                                              message: "We couldn't seem to find your account. Please try again.",
                                              preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "Okay", style: .default, handler: nil))
                self.present(alert, animated: true, completion: nil)

                self.passwordField.text = ""
                self.emailField.text = ""
            }

        }

    }
}

